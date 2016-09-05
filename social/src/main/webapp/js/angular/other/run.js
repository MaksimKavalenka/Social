'use strict';
app.run(['$cookies', '$location', '$rootScope', '$state', 'STATE', 'URL', 'FlashService', function($cookies, $location, $rootScope, $state, STATE, URL, FlashService) {
	$rootScope.isPermitted = function() {
		var restrictedPage = $.inArray($location.path(), [URL.LOGIN, URL.REGISTER]) === -1;
		var loggedIn = $rootScope.user;
		return (restrictedPage && !loggedIn);
	};

	$rootScope.user = $cookies.getObject('user');
	$rootScope.$on('$locationChangeStart', function() {
		if ($rootScope.isPermitted()) {
			$state.go(STATE.LOGIN);
		}
	});
	$rootScope.$on('$stateChangeStart', function() {
		if ($rootScope.isPermitted()) {
			$location.path(URL.LOGIN);
		}
	});
	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});
}]);