'use strict';
app.controller('ChosenController', ['$rootScope', 'UserFactory', 'FlashService', function ($rootScope, UserFactory, FlashService) {

	var self = this;
	self.usersId = [];
	$rootScope.users = [];

	$rootScope.init = function(creatorId) {
		if ($rootScope.user.id === creatorId) {
			UserFactory.getAllUsers(function(response) {
				if (response.success) {
					$rootScope.users = response.data;
				} else {
					FlashService.error(response.message);
				}
			});
		}
	};

}]);