'use strict';
app.controller('TopicController', ['$scope', '$state', 'STATE', 'TopicFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, TopicFactory, FlashService, PaginationService) {

	var self = this;
	self.topic = {};
	self.topics = [];
	self.currentPath = "";
	self.member = false;

	self.init = function(state) {
		switch (state) {
			case STATE.TOPIC_EDIT:
				var path = $state.params.path;
				self.currentPath = path;
				self.getTopicByPath(path);
				break;
			case STATE.TOPIC:
				var page = $state.params.page;
				var path = $state.params.path;
				self.checkMember(path);
				self.getTopicByPath(path);
				PaginationService.getPages(page, state);
				break;
			case STATE.TOPICS:
				var page = $state.params.page;
				self.getUserTopics(page);
				PaginationService.getPages(page, state);
				break;
			case STATE.SEARCH:
				var page = $state.params.page;
				var value = $state.params.value;
				self.getTopicsByValue(value, page);
				PaginationService.getPages(page, state);
				break;
		}
	};

	self.createTopic = function() {
		self.dataLoading = true;
		TopicFactory.createTopic(self.topic.name, self.topic.path, self.topic.description, self.topic.access, function(response) {
			if (response.success) {
				$state.go(STATE.TOPIC, {path: self.topic.path});
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.updateTopic = function() {
		self.dataLoading = true;
		TopicFactory.updateTopic(self.topic.id, self.topic.name, self.topic.path, self.topic.description, self.topic.access, function(response) {
			if (response.success) {
				$state.go(STATE.TOPIC, {path: self.topic.path});
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.editTopic = function() {
		switch ($state.current.name) {
			case STATE.TOPIC_ADD:
				self.createTopic();
				break;
			case STATE.TOPIC_EDIT:
				self.updateTopic();
				break;
		}
	};

	self.getTopicByPath = function(path) {
		TopicFactory.getTopicByPath(path, function(response) {
			if (response.success) {
				self.topic = response.data;
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

	self.editValidatePath = function() {
		return ($state.current.name === STATE.TOPIC_EDIT) && (self.currentPath === self.topic.path);
	}

	self.editPermissions = function() {
		return (($state.current.name === STATE.TOPIC_EDIT) && ($scope.user.id === self.topic.creator.id)) || $state.current.name === STATE.TOPIC_ADD;
	}

	self.init($state.current.name);

}]);