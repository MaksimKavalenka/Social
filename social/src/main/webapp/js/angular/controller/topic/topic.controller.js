'use strict';
app.controller('TopicController', ['$scope', '$state', 'STATE', 'TopicFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, TopicFactory, FlashService, PaginationService) {
	var self = this;
	self.topic = {};
	self.topics = [];

	self.init = function(state, page) {
		switch (state) {
			case STATE.TOPICS:
				self.getTopicsByCriteria('user', $scope.user.id, page);
				break;
			case STATE.TOPIC:
				self.getTopicByUrlName($state.params.urlName, page);
				break;
		}
		PaginationService.getPages(page, state);
	};

	self.getTopicByUrlName = function(urlName) {
		TopicFactory.getTopicsByCriteria(relation, id, page, function(response) {
			if (response.success) {
				self.topic = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getTopicsByCriteria = function(relation, id, page) {
		TopicFactory.getTopicsByCriteria(relation, id, page, function(response) {
			if (response.success) {
				self.topics = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $state.params.page);
}]);