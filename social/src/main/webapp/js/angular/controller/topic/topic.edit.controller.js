'use strict';
app.controller('TopicEditController', ['$location', '$scope', '$state', 'URL', 'TopicFactory', 'CookieService', 'FlashService', function($location, $scope, $state, URL, TopicFactory, CookieService, FlashService) {

	var self = this;

	self.createTopic = function() {
		self.dataLoading = true;
		TopicFactory.createTopic(self.topic.name, self.topic.path, self.topic.description, self.topic.access, $scope.user.id, function(response) {
			if (response.success) {
				$state.go(STATE.TOPIC, {path: self.topic.path});
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

}]);