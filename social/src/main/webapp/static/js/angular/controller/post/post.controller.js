'use strict';
app.controller('PostController', ['$state', 'STATE', 'PostFactory', 'TopicFactory', 'FlashService', 'TransformService', function($state, STATE, PostFactory, TopicFactory, FlashService, TransformService) {

	var self = this;
	self.posts = [];
	self.member = false;
	self.postPage = false;

	function init(state) {
		switch (state) {
			case STATE.FEED:
				var page = $state.params.page;
				self.member = true;
				getFeedPosts(page);
				break;
			case STATE.TOPIC:
				var page = $state.params.page;
				var path = $state.params.path;
				getTopicPosts(path, page);
				break;
			case STATE.POST:
				var path = $state.params.path;
				var id = $state.params.id;
				self.postPage = true;
				checkMember(path);
				getPostById(path, id);
				break;
		}
	}

	function getPostById(path, id) {
		PostFactory.getPostById(path, id, function(response) {
			if (response.success) {
				var array = [];
				array.push(response.data);
				self.posts = TransformService.transformPostArray(array, 0);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getTopicPosts(path, page) {
		PostFactory.getTopicPosts(path, page, function(response) {
			if (response.success) {
				self.posts = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getFeedPosts(page) {
		PostFactory.getFeedPosts(page, function(response) {
			if (response.success) {
				self.posts = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function checkMember(path) {
		TopicFactory.checkMember(path, function(response) {
			if (response.success) {
				self.member = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name);

}]);