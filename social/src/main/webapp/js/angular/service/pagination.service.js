'use strict';
app.service('PaginationService', ['$rootScope', function($rootScope) {

	function getPages(page, path) {
		$rootScope.pages = [];
		var from = page - 2;
		if (page <= 3) {
			from = 1;
		}
		for (var i = from; i < from + 5; i++) {
			var type = 'default';
			if (page == i) {
				type = 'active';
			}
			$rootScope.pages.push({
				number: i,
				link: path + '({page:' + i + '})',
				type: type
			});
		}
	}

	return {
		getPages: getPages
	};
}]);