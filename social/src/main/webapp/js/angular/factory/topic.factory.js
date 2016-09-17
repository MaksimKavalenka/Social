'use strict';
app.factory('TopicFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createTopic(name, path, description, access, callback) {
		if (!name || !description || !access) {
			var response = {success: false, message: MESSAGE.FORM_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.TOPICS + '/create/' + name + '/' + path + '/' + description + '/' + access + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function updateTopic(id, name, path, description, access, callback) {
		if (!id || !name || !description || !access) {
			var response = {success: false, message: MESSAGE.FORM_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.TOPICS + '/update/' + id + '/' + name + '/' + path + '/' + description + '/' + access + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
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

	function getUserTopics(page, callback) {
		$http.get(REST.TOPICS + '/user/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	function getTopicsByValue(value, page, callback) {
		$http.get(REST.TOPICS + '/search/' + value + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	function getUserTopicsCount(callback) {
		$http.get(REST.TOPICS + '/user/count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	function getUserTopicsPageCount(callback) {
		$http.get(REST.TOPICS + '/user/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	function getTopicsByValuePageCount(value, callback) {
		$http.get(REST.TOPICS + '/search/' + value + '/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	function joinTopic(path, callback) {
		$http.post(REST.TOPICS + '/join/' + path + REST.JSON_EXT)
		.success(function(response) {
			response = {success: true};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.UPDATING_TOPIC_ERROR};
			callback(response);
		});
	}

	function leaveTopic(path, callback) {
		$http.post(REST.TOPICS + '/leave/' + path + REST.JSON_EXT)
		.success(function(response) {
			response = {success: true};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.UPDATING_TOPIC_ERROR};
			callback(response);
		});
	}

	function checkPath(path, callback) {
		$http.post(REST.TOPICS + '/check_path/' + path + REST.JSON_EXT)
		.success(function(response) {
			if (response) {
				response = {success: true};
			} else {
				response = {success: false, message: MESSAGE.TAKEN_PATH_ERROR};
			}
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	function checkMember(path, callback) {
		$http.post(REST.TOPICS + '/check_member/' + path + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TOPIC_ERROR};
			callback(response);
		});
	}

	return {
		createTopic: createTopic,
		updateTopic: updateTopic,
		getTopicByPath: getTopicByPath,
		getUserTopics: getUserTopics,
		getTopicsByValue: getTopicsByValue,
		getUserTopicsCount: getUserTopicsCount,
		getUserTopicsPageCount: getUserTopicsPageCount,
		getTopicsByValuePageCount: getTopicsByValuePageCount,
		joinTopic: joinTopic,
		leaveTopic: leaveTopic,
		checkPath: checkPath,
		checkMember: checkMember
	};

}]);