package com.stathis.unipiaudiostories.ui.main.details

/**
 * This Interface contains the basic features needed to implement
 * the favorite's functionality.
 */

interface FavoritesUtil {

    /**
     * Adds a [Story] as a favorite in the firebase db ref.
     */

    fun addToFavorites()

    /**
     * Removes a [Story] as a favorite in the firebase db ref.
     */

    fun deleteFromFavorites()


    /**
     * Returns a List<[StoryDto]> from the db ref if there are entries.
     */

    fun getUserFavorites()
}