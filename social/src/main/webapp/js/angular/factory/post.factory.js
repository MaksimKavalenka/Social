'use strict';
app.factory('PostFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createPost(text, creatorId, topicId, parentPostId, callback) {
		$http.post(REST.POSTS + '/create/' + text + '/' + creatorId + '/' + topicId + '/' + parentPostId + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_POST_ERROR};
			callback(response);
		});
	}

	function getPostsByCriteria(relation, id, page, callback) {
		$http.get(REST.POSTS + '/' + relation + '/' + id + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_POST_ERROR};
			callback(response);
		});
	}

	function getFeedPosts(id, page, callback) {
		$http.get(REST.POSTS + '/feed/user/' + id + '/' + page + REST.JSON_EXT)
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
		getPostsByCriteria: getPostsByCriteria,
		getFeedPosts: getFeedPosts
	};

}]);