package org.safaproject.safa.service.search;

import java.util.List;

import org.safaproject.safa.dao.ContentDAO;
import org.safaproject.safa.model.content.Content;
import org.safaproject.safa.model.content.OrderDirections;
import org.safaproject.safa.model.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleOrderSearchStrategy extends SearchStrategy {

	@Autowired
	private ContentDAO contentDAO;

	@Override
	public List<Content> search(List<Tag> selectedTags, Integer firstResult,
			Integer maxResults, String orderBy, OrderDirections orderDirection) {

		return contentDAO.getCriteriaBuilder().withTags(selectedTags)
				.orderBy(orderDirection, orderBy).list(firstResult, maxResults);
	}
	
	public void setContentDAO(ContentDAO contentDAO) {
		this.contentDAO = contentDAO;
	}
}
