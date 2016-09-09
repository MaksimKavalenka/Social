'use strict';
app.controller('UserEditController', ['$state', 'STATE', 'UserFactory', 'CookieService', 'FlashService', function($state, STATE, UserFactory, CookieService, FlashService) {

	var self = this;

	self.login = function() {
		self.dataLoading = true;
		UserFactory.authentication(self.user.login, self.user.password, true, function(response) {
			if (response.success) {
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
		UserFactory.createUser(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CookieService.setCredentials(response.data);
				$state.go(STATE.FEED, {page: 1});
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

}]);