'use strict';
app.service('ValidatorService', ['MESSAGE', function(MESSAGE) {

	function allNotEmpty() {
		for (var i = 1; i < arguments.length; i++) {
			if (!arguments[i]) {
				var response = {success: false, message: MESSAGE.FORM_ERROR};
				arguments[0](response);
				return false;
			}
		}
		return true;
	}

	return {
		allNotEmpty: allNotEmpty
	};

}]);