package by.training.utility;

import java.util.HashSet;
import java.util.Set;

import by.training.bean.PostWithComments;
import by.training.entity.PostEntity;

public abstract class PostParser {

    public static Set<PostWithComments> parseListPostsToListPostsWithComments(
            final Set<PostEntity> posts) {
        Set<PostWithComments> postsWithComments = new HashSet<>(posts.size());
        for (PostEntity post : posts) {
            postsWithComments.add(new PostWithComments(post,
                    parseListPostsToListPostsWithComments(post.getPosts())));
        }
        return postsWithComments;
    }

}
