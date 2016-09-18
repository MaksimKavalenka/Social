'use strict';
app.controller('PaginationController', ['$rootScope', '$state', 'STATE', 'PostFactory', 'TopicFactory', 'FlashService', function($rootScope, $state, STATE, PostFactory, TopicFactory, FlashService) {

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
			case STATE.SEARCH:
				if (!$state.params.value) {
					setPages(state, 1, 0);
				} else {
					TopicFactory.getTopicsByValuePageCount($state.params.value, callback);
				}
				break;
		}
	}

	function setPages(state, page, amount) {
		$rootScope.pages = [];
		var from = ((page <= 3) || (amount < 5)) ? 1 : (page - 2);
		var to = ((from + 5) > amount) ? (amount + 1) : (from + 5);
		to = (page <= amount) ? to : (from + 5);
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
		if (amount == 1) {
			$rootScope.pages = [];
		}
	}

	init($state.current.name, $state.params.page);

}]);