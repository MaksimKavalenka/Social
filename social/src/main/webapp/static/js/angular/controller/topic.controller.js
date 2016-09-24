'use strict';
app.controller('TopicController', ['$scope', '$state', 'STATE', 'TopicFactory', 'FlashService', function($scope, $state, STATE, TopicFactory, FlashService) {

	var self = this;
	self.topic = {};
	self.topics = [];
	self.member = false;
	var currentPath = "";

	self.editTopic = function() {
		switch ($state.current.name) {
			case STATE.TOPIC_ADD:
				createTopic();
				break;
			case STATE.TOPIC_EDIT:
				updateTopic();
				break;
		}
	};

	self.joinTopic = function() {
		if (self.topic.access && !self.member) {
			TopicFactory.joinTopic($state.params.path, function(response) {
				if (response.success) {
					self.member = true;
				} else {
					FlashService.error(response.message);
				}
			});
		}
	};

	self.leaveTopic = function() {
		if (self.member) {
			TopicFactory.leaveTopic($state.params.path, function(response) {
				if (response.success) {
					self.member = false;
				} else {
					FlashService.error(response.message);
				}
			});
		}
	};

	self.validatePath = function() {
		var id = 'path';
		var flag = ($state.current.name === STATE.TOPIC_EDIT) && (currentPath === document.getElementById(id).value);
		if (flag) {
			document.getElementById(id).className = 'ng-valid';
		}
		return flag;
	};

	self.permissions = function() {
		return (($state.current.name === STATE.TOPIC_EDIT) && ($scope.user.id === self.topic.creator.id)) || $state.current.name === STATE.TOPIC_ADD;
	};

	function init(state) {
		switch (state) {
			case STATE.TOPIC_EDIT:
				var path = $state.params.path;
				currentPath = path;
				getTopicByPath(path);
				break;
			case STATE.TOPIC:
				var page = $state.params.page;
				var path = $state.params.path;
				checkMember(path);
				getTopicByPath(path);
				break;
			case STATE.TOPICS:
				var page = $state.params.page;
				getUserTopics(page);
				break;
			case STATE.SEARCH:
				var page = $state.params.page;
				var value = $state.params.value;
				getTopicsByValue(value, page);
				break;
		}
	}

	function createTopic() {
		self.dataLoading = true;
		TopicFactory.createTopic(self.topic.name, self.topic.path, self.topic.description, self.topic.access, function(response) {
			if (response.success) {
				$state.go(STATE.TOPIC, {path: self.topic.path});
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	}

	function updateTopic() {
		self.dataLoading = true;
		TopicFactory.updateTopic(self.topic.id, self.topic.name, self.topic.path, self.topic.description, self.topic.access, function(response) {
			if (response.success) {
				$state.go(STATE.TOPIC, {path: self.topic.path});
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	}

	function getTopicByPath(path) {
		TopicFactory.getTopicByPath(path, function(response) {
			if (response.success) {
				$state.current.title = response.data.name;
				self.topic = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserTopics(page) {
		TopicFactory.getUserTopics(page, function(response) {
			if (response.success) {
				self.topics = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getTopicsByValue(value, page) {
		TopicFactory.getTopicsByValue(value, page, function(response) {
			if (response.success) {
				self.topics = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function checkMember(path) {
		TopicFactory.checkMember(path, function(response) {
			if (response.success) {
				self.member = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name);

}]);