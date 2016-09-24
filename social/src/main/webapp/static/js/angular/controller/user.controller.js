'use strict';
app.controller('UserController', ['$rootScope', '$scope', '$state', 'STATE', 'UserFactory', 'CookieService', 'FileService', 'FlashService', function($rootScope, $scope, $state, STATE, UserFactory, CookieService, FileService, FlashService) {

	var self = this;
	self.user = {};
	var currentLogin = "";

	function init(state) {
		switch (state) {
			case STATE.PROFILE:
				getUser();
				break;
		}
	}

	self.login = function() {
		self.dataLoading = true;
		UserFactory.authentication(self.user.login, self.user.password, self.user.remember, function(response) {
			if (response.success) {
				$rootScope.user = {id: response.data.id};
				$state.go(STATE.FEED, {page: 1});
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.logout = function() {
		UserFactory.logout();
		$state.go(STATE.LOGIN);
	};

	self.register = function() {
		self.dataLoading = true;
		UserFactory.createUser(self.user.login, self.user.password, self.user.confirmPassword, function(response) {
			if (response.success) {
				self.login();
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.update = function() {
		var callback = function(response) {
			if (response.success) {
				$state.go(STATE.FEED, {page: 1});
			} else {
				FlashService.error(response.message);
			}
		}
		switch ($state.current.name) {
			case STATE.PROFILE:
				if (self.user.change) {
					UserFactory.updateUser(self.user.login, self.user.currentPassword, self.user.password, self.user.confirmPassword, callback);
				} else {
					UserFactory.updateUserLogin(self.user.login, self.user.currentPassword, callback);
				}
				break;
			case STATE.PROFILE_PHOTO:
				FileService.uploadFile($scope.photoFile, function(response) {
					if (response.success) {
						UserFactory.updateUserPhoto(self.user.photo.replace(/^C:\\fakepath\\/i, ''), callback);
					} else {
						FlashService.error(response.message);
					}
				});
				break;
		}
	};

	self.validateLogin = function() {
		var id = 'login';
		var flag = (currentLogin === document.getElementById(id).value);
		if (flag) {
			document.getElementById(id).className = 'ng-valid';
		}
		return flag;
	};

	function getUser() {
		UserFactory.getUser(function(response) {
			if (response.success) {
				currentLogin = response.data.login;
				self.user = {login: response.data.login};
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name);

}]);