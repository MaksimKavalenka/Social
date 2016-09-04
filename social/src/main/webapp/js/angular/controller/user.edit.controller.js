'use strict';
app.controller('UserEditController', ['$location', '$state', 'URL', 'UserFactory', 'CookieService', 'FlashService', function($location, $state, URL, UserFactory, CookieService, FlashService) {
	var self = this;

	self.login = function() {
		self.dataLoading = true;
		UserFactory.authentication(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CookieService.setCredentials(response.data);
				$location.path(URL.HOME_PAGE);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.logout = function() {
		CookieService.clearCredentials();
		$location.path(URL.LOGIN);
	};

	self.register = function() {
		self.dataLoading = true;
		UserFactory.createUser(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CookieService.setCredentials(response.data);
				$location.path(URL.HOME_PAGE);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

}]);