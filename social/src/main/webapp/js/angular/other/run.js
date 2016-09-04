'use strict';
app.run(['$cookies', '$location', '$rootScope', 'URL', 'FlashService', function($cookies, $location, $rootScope, URL, FlashService) {
	$rootScope.user = $cookies.getObject('user');
	$rootScope.$on('$stateChangeStart', function() {
		if (!$rootScope.user) {
			$location.path(URL.LOGIN);
		}
	});
	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});
}]);