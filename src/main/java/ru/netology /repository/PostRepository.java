package ru.netology.repository;

import ru.netology.model.Post;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final AtomicLong postID;
    private final Map<Long, Post> posts;

    public PostRepository() {
        postID = new AtomicLong(0);
        posts = new ConcurrentHashMap<>();
    }

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        long postPresentID = post.getId();
        if (postPresentID > 0 && posts.containsKey(postPresentID)) {
            posts.replace(postPresentID, post);
        } else {
            long newPostID = postPresentID == 0 ? postID.incrementAndGet() : postPresentID;
            post.setId(newPostID);
            posts.put(newPostID, post);
        }
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}