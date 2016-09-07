'use strict';
app.factory('TopicFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createTopic(name, path, description, access, creatorId, callback) {
		$http.post(REST.TOPICS + '/create/' + name + '/' + path + '/' + description + '/' + access + '/' + creatorId + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_TOPIC_ERROR};
			callback(response);
		});
	}

	function getTopicByPath(path, callback) {
		$http.get(REST.TOPICS + '/' + path + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	function getTopicsByCriteria(relation, id, page, callback) {
		$http.get(REST.TOPICS + '/' + relation + '/' + id + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	function checkPath(path, callback) {
		$http.post(REST.TOPICS + '/check_path/' + path + REST.JSON_EXT)
		.success(function(response) {
			if (response) {
				response = {success: true};
			} else {
				response = {success: false, message: MESSAGE.TAKEN_TOPIC_URL_NAME_ERROR};
			}
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	return {
		createTopic: createTopic,
		getTopicByPath: getTopicByPath,
		getTopicsByCriteria: getTopicsByCriteria,
		checkPath: checkPath
	};

}]);