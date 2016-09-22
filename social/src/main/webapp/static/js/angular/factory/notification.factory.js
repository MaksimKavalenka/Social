'use strict';
app.factory('NotificationFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function sendInvitations(usersId, topicPath, callback) {
		if (!usersId) {
			var response = {success: false, message: MESSAGE.FORM_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.NOTIFICATIONS + '/create/' + usersId + '/' + topicPath + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function deleteNotification(id, callback) {
		$http.post(REST.NOTIFICATIONS + '/delete/' + id + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.DELETING_NOTIFICATION_ERROR};
			callback(response);
		});
	}

	function getUserNotifications(page, callback) {
		$http.get(REST.NOTIFICATIONS + '/user/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_NOTIFICATION_ERROR};
			callback(response);
		});
	}

	function getUserNotificationsPageCount(callback) {
		$http.get(REST.NOTIFICATIONS + '/user/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_NOTIFICATION_ERROR};
			callback(response);
		});
	}

	return {
		sendInvitations: sendInvitations,
		deleteNotification: deleteNotification,
		getUserNotifications: getUserNotifications,
		getUserNotificationsPageCount: getUserNotificationsPageCount
	};

}]);