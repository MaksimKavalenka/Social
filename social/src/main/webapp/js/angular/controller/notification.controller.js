'use strict';
app.controller('NotificationController', ['$rootScope', '$state', 'STATE', 'NotificationFactory', 'FlashService', function($rootScope, $state, STATE, NotificationFactory, FlashService) {

	var self = this;
	self.notifications = [];

	self.sendInvitations = function() {
		var usersId = [];
		for (var i = 0; i < $rootScope.users.length; i++) {
			usersId.push({id: $rootScope.users[i].id});
		}
		NotificationFactory.sendInvitations(angular.toJson(usersId), $state.params.path, function(response) {
			if (response.success) {
				self.count = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.deleteNotification = function(id) {
		NotificationFactory.deleteNotification(id, function(response) {
			if (response.success) {
				self.notifications = self.notifications.filter(function(obj) {
					return obj.id !== id;
				});
			} else {
				FlashService.error(response.message);
			}
		});
	};

	function init(state) {
		switch (state) {
			case STATE.NOTIFICATIONS:
				var page = $state.params.page;
				getUserNotifications(page);
				break;
		}
	}

	function getUserNotifications(page) {
		NotificationFactory.getUserNotifications(page, function(response) {
			if (response.success) {
				self.notifications = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name);

}]);