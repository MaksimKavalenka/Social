'use strict';
app.controller('PostController', ['$scope', '$state', 'STATE', 'PostFactory', 'TopicFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, PostFactory, TopicFactory, FlashService, PaginationService) {

	var self = this;
	self.post = {text: ""};
	self.posts = [];
	self.form = false;

	self.init = function(state) {
		switch (state) {
			case STATE.FEED:
				var page = $state.params.page;
				self.getFeedPosts(page);
				PaginationService.getPages(page, state);
				break;
			case STATE.TOPIC:
				var page = $state.params.page;
				var path = $state.params.path;
				self.getTopicPosts(path, page);
				PaginationService.getPages(page, state);
				break;
		}
	};

	self.createPost = function(topicId, parentPostId) {
		self.dataLoading = true;
		PostFactory.createPost(self.post.text, topicId, parentPostId, function(response) {
			if (response.success) {
				$state.reload();
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.updatePost = function(id) {
		self.dataLoading = true;
		PostFactory.updatePost(id, self.post.text, function(response) {
			if (response.success) {
				$state.reload();
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
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

	self.creationForm = function(access) {
		if (!access) {
			return;
		}
		self.form = true;
	};

	self.editionForm = function(post) {
		if ($scope.user.id !== post.creator.id) {
			return;
		}
		self.form = true;
		self.post = {text: post.text};
	};

	self.hideForm = function() {
		self.form = false;
		self.post = {text: ""};
	};

	self.init($state.current.name);

}]);