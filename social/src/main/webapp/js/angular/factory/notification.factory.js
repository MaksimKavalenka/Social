'use strict';
app.factory('NotificationFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createNotification(userId, topicPath, callback) {
		if (!text || !topicId || !parentPostId) {
			var response = {success: false, message: MESSAGE.FORM_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.NOTIFICATIONS + '/create/' + userId + '/' + topicPath + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	return {
		createNotification: createNotification
	};

}]);