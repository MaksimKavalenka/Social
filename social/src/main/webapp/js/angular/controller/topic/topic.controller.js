'use strict';
app.controller('TopicController', ['$scope', '$state', 'STATE', 'TopicFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, TopicFactory, FlashService, PaginationService) {

	var self = this;
	self.topic = {};
	self.topics = [];
	self.member = false;

	self.init = function(state, page) {
		switch (state) {
			case STATE.TOPIC:
				self.checkMember($state.params.path);
				self.getTopicByPath($state.params.path);
				break;
			case STATE.TOPICS:
				self.getUserTopics(page);
				break;
			case STATE.SEARCH:
				self.getTopicsByValue($state.params.value, $state.params.page);
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

	self.getUserTopics = function(page) {
		TopicFactory.getUserTopics(page, function(response) {
			if (response.success) {
				self.topics = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getTopicsByValue = function(value, page) {
		TopicFactory.getTopicsByValue(value, page, function(response) {
			if (response.success) {
				self.topics = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.joinTopic = function() {
		TopicFactory.joinTopic($state.params.path, function(response) {
			if (response.success) {
				self.member = true;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.leaveTopic = function() {
		TopicFactory.leaveTopic($state.params.path, function(response) {
			if (response.success) {
				self.member = false;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.checkMember = function(path) {
		TopicFactory.checkMember(path, function(response) {
			if (response.success) {
				self.member = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $state.params.page);

}]);