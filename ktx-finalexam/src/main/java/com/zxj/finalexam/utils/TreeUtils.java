package com.zxj.finalexam.utils;

import com.zxj.finalexam.entity.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TreeUtils {
    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    /**
     * 把评论信息的集合转化为一个树
     */
    public static Node buildTree(List<Comment> comments, long questionId) {
        Node tree = new Node();

        List<Node> parentNodes = new ArrayList<>(comments.size());
        //得到所有的父级评论 将评论转为Node 全放入parentNodes
        comments.stream().filter(comment -> comment.getId() == questionId)
                .forEach(comment -> {
                    parentNodes.add(buildNode(comment));

                });
        //遍历parentNodes 每个node设孩子评论
        parentNodes.forEach(parentNode -> parentNode.setChildren(getChildren(parentNode, comments)));
        //返回该问题所有的评论
        tree.setChildren(parentNodes);
        return tree;
    }

    private static List<Node> getChildren(Node parentNode, List<Comment> comments) {
        List<Node> childrenNodes = new ArrayList<>();
        //将pid=当前父级评论的评论过滤出来 转化为node并全部放入childrenNodes
        comments.stream().filter(comment -> parentNode.getId().equals(comment.getParentId()))
                .forEach(comment -> {
                    childrenNodes.add(buildNode(comment));
                });
        //
        childrenNodes.forEach(child -> child.setChildren(getChildren(child, comments)));
        return childrenNodes;
    }


    private static Node buildNode(Comment comment) {
        Node node = new Node();
        node.setId(comment.getId());
        node.setParentId(comment.getParentId());
        node.setObject(comment);
        node.setChildren(null);
        return node;
    }
}
