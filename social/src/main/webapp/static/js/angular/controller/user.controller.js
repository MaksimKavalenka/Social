'use strict';
app.controller('UserController', ['$rootScope', '$state', 'STATE', 'UserFactory', 'CookieService', 'FlashService', function($rootScope, $state, STATE, UserFactory, CookieService, FlashService) {

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
		if (self.user.change) {
			UserFactory.updateUser(self.user.login, self.user.currentPassword, self.user.password, self.user.confirmPassword, callback);
		} else {
			UserFactory.updateUserLogin(self.user.login, callback);
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