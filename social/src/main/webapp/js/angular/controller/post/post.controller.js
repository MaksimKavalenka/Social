'use strict';
app.controller('PostController', ['$scope', '$state', 'STATE', 'PostFactory', 'TopicFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, PostFactory, TopicFactory, FlashService, PaginationService) {

	var self = this;
	self.posts = [];

	self.init = function(state, page) {
		switch (state) {
			case STATE.FEED:
				self.getFeedPosts($scope.user.id, page);
				break;
			case STATE.TOPIC:
				self.getPostsByCriteria('topic', $state.params.path, page);
				break;
		}
		PaginationService.getPages(page, state);
	};

	self.getPostsByCriteria = function(relation, id, page) {
		PostFactory.getPostsByCriteria(relation, id, page, function(response) {
			if (response.success) {
				self.posts = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getFeedPosts = function(userId, page) {
		PostFactory.getFeedPosts(userId, page, function(response) {
			if (response.success) {
				self.posts = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $state.params.page);

}]);