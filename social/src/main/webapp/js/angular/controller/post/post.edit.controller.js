'use strict';
app.controller('PostEditController', ['$scope', '$state', 'STATE', 'PostFactory', 'FlashService', function($scope, $state, STATE, PostFactory, FlashService) {

	var self = this;
	self.post = {text: ""};
	self.form = false;

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

}]);