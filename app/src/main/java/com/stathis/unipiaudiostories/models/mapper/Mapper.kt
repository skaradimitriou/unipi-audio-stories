package com.stathis.unipiaudiostories.models.mapper

/**
 * A : Data Layer's model (Dto)
 * B : Domain Layer's model (Entity)
 */

interface Mapper<A, B> {

    /**
     * Method that takes the model from the Data layer (Dto - nullable values)
     * as a parameter and returns the Domain Model (non-null values).
     */

    fun fromDataToDomainModel(dataModel: A): B

    /**
     * Method that takes the model from the Domain Layer as a parameter and
     * maps it into the Data layer's object (Dto)
     */

    fun fromDomainToDataModel(domainModel: B): A
}