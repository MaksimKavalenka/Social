'use strict';
app.factory('PostFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createPost(text, topicId, parentPostId, callback) {
		$http.post(REST.POSTS + '/create/' + text + '/' + topicId + '/' + parentPostId + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_POST_ERROR};
			callback(response);
		});
	}

	function updatePost(id, text, callback) {
		$http.post(REST.POSTS + '/update/' + id + '/' + text + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.UPDATING_POST_ERROR};
			callback(response);
		});
	}

	function getTopicPosts(path, page, callback) {
		$http.get(REST.POSTS + '/topic/' + path + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_POST_ERROR};
			callback(response);
		});
	}

	function getFeedPosts(page, callback) {
		$http.get(REST.POSTS + '/feed/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_POST_ERROR};
			callback(response);
		});
	}

	return {
		createPost: createPost,
		updatePost: updatePost,
		getTopicPosts: getTopicPosts,
		getFeedPosts: getFeedPosts
	};

}]);