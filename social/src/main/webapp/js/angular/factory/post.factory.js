'use strict';
app.factory('PostFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function getPostsByCriteria(relation, id, page, callback) {
		$http.get(REST.POSTS + '/' + relation + '/' + id + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_POST_ERROR};
			callback(response);
		});
	}

	return {
		getPostsByCriteria: getPostsByCriteria
	};
}]);