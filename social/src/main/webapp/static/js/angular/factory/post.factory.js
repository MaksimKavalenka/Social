'use strict';
app.factory('PostFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createPost(text, topicPath, parentPostId, callback) {
		if (!text || !parentPostId) {
			var response = {success: false, message: MESSAGE.FORM_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.POSTS + '/create/' + text + '/' + topicPath + '/' + parentPostId + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function updatePost(id, text, callback) {
		if (!id || !text) {
			var response = {success: false, message: MESSAGE.FORM_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.POSTS + '/update/' + id + '/' + text + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function deletePost(id, path, callback) {
		if (!id) {
			var response = {success: false, message: MESSAGE.FORM_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.POSTS + '/delete/' + id + '/' + path + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: MESSAGE.DELETING_POST_ERROR};
			callback(data);
		});
	}

	function getPostById(path, id, callback) {
		$http.get(REST.POSTS + '/' + path + '/' + id + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: response.message};
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

	function getTopicPostsPageCount(path, callback) {
		$http.get(REST.POSTS + '/topic/' + path + '/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_POST_ERROR};
			callback(response);
		});
	}

	function getFeedPostsPageCount(callback) {
		$http.get(REST.POSTS + '/feed/page_count' + REST.JSON_EXT)
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
		deletePost: deletePost,
		getPostById: getPostById,
		getTopicPosts: getTopicPosts,
		getFeedPosts: getFeedPosts,
		getTopicPostsPageCount: getTopicPostsPageCount,
		getFeedPostsPageCount: getFeedPostsPageCount
	};

}]);