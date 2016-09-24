'use strict';
app.service('TransformService', [function() {

	function transformPostArray(posts, level) {
		var transformedPosts = [];
		for (var i = 0; i < posts.length; i++) {
			transformedPosts.push({
				id: posts[i].id,
				text: posts[i].text,
				date: posts[i].date,
				creator: posts[i].creator,
				topic: posts[i].topic,
				level: level
			});
			if (posts[i].posts.length > 0) {
				transformedPosts = transformedPosts.concat(transformPostArray(posts[i].posts, level + 1));
			}
		}
		return transformedPosts;
	}

	return {
		transformPostArray: transformPostArray
	};

}]);