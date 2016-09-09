'use strict';
app.controller('PostEditController', ['$scope', '$state', 'STATE', 'PostFactory', 'FlashService', function($scope, $state, STATE, PostFactory, FlashService) {

	var self = this;
	self.form = false;

	self.createPost = function(topicId, parentPostId) {
		self.dataLoading = true;
		PostFactory.createPost(self.post.text, $scope.user.id, topicId, parentPostId, function(response) {
			if (response.success) {
				$state.reload();
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.showForm = function(visibility) {
		self.form = visibility;
		if (!visibility) {
			self.post = {text: ""};
		}
	};

}]);