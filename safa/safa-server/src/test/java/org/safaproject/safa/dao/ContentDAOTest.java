package org.safaproject.safa.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safaproject.safa.model.content.Comment;
import org.safaproject.safa.model.content.Content;
import org.safaproject.safa.model.content.Resource;
import org.safaproject.safa.model.content.builder.CommentBuilder;
import org.safaproject.safa.model.content.builder.ContentBuilder;
import org.safaproject.safa.model.content.builder.ResourceBuilder;
import org.safaproject.safa.model.indicator.Indicator;
import org.safaproject.safa.model.indicator.IndicatorType;
import org.safaproject.safa.model.indicator.builder.IndicatorBuilder;
import org.safaproject.safa.model.indicator.builder.IndicatorTypeBuilder;
import org.safaproject.safa.model.tag.Tag;
import org.safaproject.safa.model.tag.TagDataTypes;
import org.safaproject.safa.model.tag.TagType;
import org.safaproject.safa.model.tag.builder.TagBuilder;
import org.safaproject.safa.model.tag.builder.TagTypeBuilder;
import org.safaproject.safa.model.user.User;
import org.safaproject.safa.model.user.builder.UserBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:safa-unit-test-context.xml")
@Transactional
public class ContentDAOTest {

	@Autowired
	private ContentDAO contentDao;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private CommentDAO commentDao;

	@Autowired
	private TagTypeDAO tagTypeDAO;

	@Autowired
	private TagDAO tagDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	@Autowired
	private IndicatorTypeDAO indicatorTypeDAO;

	@Autowired
	private IndicatorDAO indicatorDAO;

	private User testingUser = new UserBuilder().withUsername("Test").build();

	private TagType testTagType = new TagTypeBuilder()
			.withTagName("Resource Type").withTagDataType(TagDataTypes.STRING)
			.build();

	private Tag testResourceType = new TagBuilder().withTagType(testTagType)
			.withValue("PDF").build();

	private Resource testResource = new ResourceBuilder().withDescription("")
			.withSize(10L).withUrl("http://tuvieja.com")
			.withResourceType(testResourceType).build();

	@Before
	public void init() {
		userDao.save(testingUser);
		tagTypeDAO.save(testTagType);
		tagDAO.save(testResourceType);
		resourceDAO.save(testResource);
	}

	@Test
	public void shallSearch() {
		IndicatorType indicatorType = new IndicatorTypeBuilder()
				.withIndicatorName("Frula").withMaxValue(700).withMinValue(2)
				.build();
		indicatorTypeDAO.save(indicatorType);

		Indicator indicator = new IndicatorBuilder().withValue(250)
				.withIndicatorType(indicatorType).build();
		indicatorDAO.save(indicator);

		Indicator indicator2 = new IndicatorBuilder().withValue(450)
				.withIndicatorType(indicatorType).build();
		indicatorDAO.save(indicator2);

		Content content = new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withThumbnail(testResource)
				.withTitle("Design Patterns")
				.withIndicators(
						new HashSet<Indicator>(Arrays.asList(indicator)))
				.withTags(new HashSet<Tag>(Arrays.asList(testResourceType)))
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withUploadDate(new Date()).withUser(testingUser).build();
		contentDao.save(content);

		Tag testTag = new TagBuilder().withTagType(testTagType)
				.withValue("ASD").build();
		tagDAO.save(testTag);

		Content content2 = new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc2")
				.withReviewed(true)
				.withThumbnail(testResource)
				.withTitle("Design Patterns")
				.withIndicators(
						new HashSet<Indicator>(Arrays.asList(indicator2)))
				.withTags(new HashSet<Tag>(Arrays.asList(testTag)))
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withUploadDate(new Date()).withUser(testingUser).build();
		contentDao.save(content2);

		List<Tag> tags = new ArrayList<Tag>();
		tags.add(testResourceType);

		List<Content> contents = contentDao.search(tags, 0, 10);

		Assert.assertEquals(1, contents.size());
		Assert.assertEquals(content, Iterables.getOnlyElement(contents));
	}

	@Test
	public void shallCreateContent() {
		Content content = new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withThumbnail(testResource)
				.withTitle("Design Patterns")
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withUploadDate(new Date()).withUser(testingUser).build();

		contentDao.save(content);

		Content contentFromDB = contentDao.findById(content.getId());

		Assert.assertEquals(content, contentFromDB);
	}

	@Test
	public void shallFindAll() {
		contentDao.save(new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withTitle("Design Patterns")
				.withUploadDate(new Date())
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withThumbnail(testResource).withUser(testingUser).build());
		contentDao.save(new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withTitle("Clean Code")
				.withUploadDate(new Date())
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withThumbnail(testResource).withUser(testingUser).build());

		List<Content> all = contentDao.findAll();

		Assert.assertEquals(2, all.size());
	}

	@Test
	public void shallDeleteContent() {
		Content content = new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withTitle("Design Patterns")
				.withUploadDate(new Date())
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withThumbnail(testResource).withUser(testingUser).build();
		contentDao.save(content);

		Content content2 = new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withTitle("Clean Code")
				.withUploadDate(new Date())
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withThumbnail(testResource).withUser(testingUser).build();

		contentDao.save(content2);

		Assert.assertEquals(Long.valueOf(2), contentDao.countAll());

		contentDao.delete(content);

		Assert.assertEquals(Long.valueOf(1), contentDao.countAll());

		contentDao.delete(content2);

		Assert.assertEquals(Long.valueOf(0), contentDao.countAll());
	}

	@Test
	public void shallUpdateContent() {
		String modifiedTitle = "Clean Code";
		Content content = new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withTitle("Design Patterns")
				.withUploadDate(new Date())
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withThumbnail(testResource).withUser(testingUser).build();

		contentDao.save(content);

		content.setTitle(modifiedTitle);
		contentDao.save(content);

		Assert.assertEquals(modifiedTitle, contentDao.findAll().get(0)
				.getTitle());
	}

	@Test
	public void shallRetrieveContentWithTagsCriteria() {

		TagType universidad = new TagTypeBuilder().withTagName("Universidad")
				.withTagDataType(TagDataTypes.STRING).build();
		tagTypeDAO.save(universidad);

		Tag utn = new TagBuilder().withTagType(universidad).withValue("UTN")
				.build();
		tagDAO.save(utn);

		Tag uade = new TagBuilder().withTagType(universidad).withValue("UADE")
				.build();
		tagDAO.save(uade);

		Content content = new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withTitle("Design Patterns")
				.withUploadDate(new Date())
				.withTags(new HashSet<Tag>(Arrays.asList(utn)))
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withThumbnail(testResource).withUser(testingUser).build();

		contentDao.save(content);

		Assert.assertEquals(1,
				contentDao.getCriteriaBuilder().withTag(utn).list()
						.size());

		Assert.assertEquals(0,
				contentDao.getCriteriaBuilder().withTag(uade).list()
						.size());

		content.setTags(new HashSet<Tag>(Arrays.asList(utn, uade)));
		contentDao.update(content);

		Assert.assertEquals(1,
				contentDao.getCriteriaBuilder().withTag(utn).list()
						.size());
	}

	@Test
	public void shallCascadeComments() {
		Comment comment = new CommentBuilder().withUser(testingUser)
				.withCommentDate(new Date())
				.withCommentText("This book is the post!").build();
		Set<Comment> comments = new HashSet<Comment>();
		comments.add(comment);

		contentDao.save(new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withTitle("Design Patterns")
				.withUploadDate(new Date())
				.withThumbnail(testResource)
				.withUser(testingUser)
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withComments(comments).build());

		Assert.assertEquals(1, contentDao.findAll().get(0).getComments().size());
		Assert.assertEquals(1, commentDao.findAll().size());

		commentDao.delete(comment);
	}

	@Test(expected = PersistenceException.class)
	public void shallFailBecauseOfNullTitle() {
		Content content = new ContentBuilder()
				.withAvailable(true)
				.withDescription("Desc")
				.withReviewed(true)
				.withTitle("Design Patterns")
				.withUploadDate(new Date())
				.withResources(
						new HashSet<Resource>(Arrays.asList(testResource)))
				.withThumbnail(testResource).withUser(testingUser).build();

		content.setTitle(null);
		contentDao.save(content);
	}

}
