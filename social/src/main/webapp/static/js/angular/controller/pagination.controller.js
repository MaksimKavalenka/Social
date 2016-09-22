'use strict';
app.controller('PaginationController', ['$rootScope', '$state', 'STATE', 'NotificationFactory', 'PostFactory', 'TopicFactory', 'FlashService', function($rootScope, $state, STATE, NotificationFactory, PostFactory, TopicFactory, FlashService) {

	function init(state, page) {
		var callback = function(response) {
			if (response.success) {
				setPages(state, page, response.data);
			} else {
				FlashService.error(response.message);
			}
		}
		switch (state) {
			case STATE.FEED:
				PostFactory.getFeedPostsPageCount(callback);
				break;
			case STATE.TOPIC:
				PostFactory.getTopicPostsPageCount($state.params.path, callback);
				break;
			case STATE.TOPICS:
				TopicFactory.getUserTopicsPageCount(callback);
				break;
			case STATE.NOTIFICATIONS:
				NotificationFactory.getUserNotificationsPageCount(callback);
				break;
			case STATE.SEARCH:
				TopicFactory.getTopicsByValuePageCount($state.params.value, callback);
				break;
		}
	}

	function setPages(state, page, count) {
		$rootScope.pages = [];
		if (count === 0) {
			count = 1;
		}
		var from = ((page <= 3) || (count < 5)) ? 1 : (page - 2);
		var to = ((from + 5) > count) ? (count + 1) : (from + 5);
		to = (page <= count) ? to : (from + 5);
		for (var i = from; i < to; i++) {
			var type = 'default';
			if (page == i) {
				type = 'active';
			}
			$rootScope.pages.push({
				number: i,
				link: state + '({page:' + i + '})',
				type: type
			});
		}
		if (count === 1) {
			$rootScope.pages = [];
		}
	}

	init($state.current.name, $state.params.page);

}]);