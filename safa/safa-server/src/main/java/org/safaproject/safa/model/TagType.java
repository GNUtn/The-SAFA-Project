package org.safaproject.safa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This class defines the type of a tag.
 * 
 * @author reyiyo
 * 
 */
@Entity
@Table(name = "TAG_TYPE")
public class TagType {

	/**
	 * The TagType Name cannot be changed/updated because it is the ID of the
	 * entity. To change it, it is needed to remove the old one and create a new
	 * one
	 */
	@Id
	@Column(name = "tagName")
	private String tagName;

	@Column(name = "tagDataType")
	@Enumerated(EnumType.STRING)
	@NotNull
	// TODO: Investigate why this constraint is not being evaluated
	private TagDataTypeEnum tagDataType;

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName
	 *            the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @return the tagType
	 */
	public TagDataTypeEnum getTagDataType() {
		return tagDataType;
	}

	/**
	 * @param tagType
	 *            the tagType to set
	 */
	public void setTagDataType(TagDataTypeEnum tagDataType) {
		this.tagDataType = tagDataType;
	}

}