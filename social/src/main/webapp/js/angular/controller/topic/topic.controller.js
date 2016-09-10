'use strict';
app.controller('TopicController', ['$scope', '$state', 'STATE', 'TopicFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, TopicFactory, FlashService, PaginationService) {

	var self = this;
	self.topic = {};
	self.topics = [];

	self.init = function(state, page) {
		switch (state) {
			case STATE.TOPIC:
				self.getTopicByPath($state.params.path);
				break;
			case STATE.TOPICS:
				self.getTopicsByCriteria('user', $scope.user.id, page);
				break;
			case STATE.SEARCH:
				self.getTopicsByValue($state.params.value, $scope.user.id, $state.params.page);
				break;
		}
		PaginationService.getPages(page, state);
	};

	self.getTopicByPath = function(path) {
		TopicFactory.getTopicByPath(path, function(response) {
			if (response.success) {
				if (response.data) {
					self.topic = response.data;
				} else {
					$state.go(STATE.TOPICS);
				}
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

	self.getTopicsByValue = function(value, idUser, page) {
		TopicFactory.getTopicsByValue(value, idUser, page, function(response) {
			if (response.success) {
				self.topics = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $state.params.page);

}]);