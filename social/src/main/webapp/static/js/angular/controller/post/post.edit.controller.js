'use strict';
app.controller('PostEditController', ['$scope', '$state', 'PostFactory', 'FlashService', function($scope, $state, PostFactory, FlashService) {

	var self = this;
	self.post = {text: ""};
	self.form = false;

	self.createPost = function(parentPostId) {
		self.dataLoading = true;
		PostFactory.createPost(self.post.text, $state.params.path, parentPostId, function(response) {
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

	self.deletePost = function(id) {
		PostFactory.deletePost(id, $state.params.path, function(response) {
			if (response.success) {
				$state.reload();
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

}]);