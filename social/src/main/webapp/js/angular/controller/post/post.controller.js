'use strict';
app.controller('PostController', ['$state', 'STATE', 'PostFactory', 'FlashService', function($state, STATE, PostFactory, FlashService) {

	var self = this;
	self.posts = [];

	function init(state) {
		switch (state) {
			case STATE.FEED:
				var page = $state.params.page;
				getFeedPosts(page);
				break;
			case STATE.TOPIC:
				var page = $state.params.page;
				var path = $state.params.path;
				getTopicPosts(path, page);
				break;
		}
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

	init($state.current.name);

}]);