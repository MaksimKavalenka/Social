'use strict';
app.controller('PostController', ['$scope', '$state', 'STATE', 'PostFactory', 'TopicFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, PostFactory, TopicFactory, FlashService, PaginationService) {

	var self = this;
	self.posts = [];

	self.init = function(state, page) {
		switch (state) {
			case STATE.FEED:
				self.getFeedPosts(page);
				break;
			case STATE.TOPIC:
				self.getTopicPosts($state.params.path, page);
				break;
		}
		PaginationService.getPages(page, state);
	};

	self.getTopicPosts = function(path, page) {
		PostFactory.getTopicPosts(path, page, function(response) {
			if (response.success) {
				self.posts = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getFeedPosts = function(page) {
		PostFactory.getFeedPosts(page, function(response) {
			if (response.success) {
				self.posts = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $state.params.page);

}]);