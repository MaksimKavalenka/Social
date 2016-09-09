'use strict';
app.service('ObserverService', ['$rootScope', '$q', '$window', function($rootScope, $q, $window) {
	return {
		'responseError': function(errorResponse) {
			switch (errorResponse.status) {
				case 403:
					if ($window.location.hash.indexOf("login") == -1) {
						$rootScope.targetUrl = $window.location.hash;
					}
					$window.location = '#/login';
					break;
			}
			return $q.reject(errorResponse);
		}
	};
}]);