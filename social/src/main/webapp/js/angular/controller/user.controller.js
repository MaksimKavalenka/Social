'use strict';
app.controller('UserController', ['$rootScope', '$state', 'STATE', 'UserFactory', 'CookieService', 'FlashService', function($rootScope, $state, STATE, UserFactory, CookieService, FlashService) {

	var self = this;

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

}]);