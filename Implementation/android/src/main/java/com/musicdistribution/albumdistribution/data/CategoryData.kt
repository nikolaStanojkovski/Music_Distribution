package com.musicdistribution.albumdistribution.data

import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.model.Category
import com.musicdistribution.albumdistribution.model.CategoryItem

class CategoryData {
    companion object {
        val creatorCategoryData = mutableListOf<Category>(
            Category(
                "Your Published Songs", mutableListOf(
                    CategoryItem(1, R.drawable.icon),
                    CategoryItem(2, R.drawable.icon),
                    CategoryItem(3, R.drawable.icon),
                    CategoryItem(4, R.drawable.icon),
                    CategoryItem(5, R.drawable.icon),
                )
            ),
            Category(
                "Your Published Albums", mutableListOf(
                    CategoryItem(6, R.drawable.icon),
                    CategoryItem(7, R.drawable.icon),
                    CategoryItem(8, R.drawable.icon),
                    CategoryItem(9, R.drawable.icon),
                    CategoryItem(10, R.drawable.icon)
                )
            ),
            Category(
                "Your Songs", mutableListOf(
                    CategoryItem(11, R.drawable.icon),
                    CategoryItem(12, R.drawable.icon),
                    CategoryItem(13, R.drawable.icon),
                    CategoryItem(14, R.drawable.icon),
                    CategoryItem(15, R.drawable.icon)
                )
            ),
            Category(
                "Your Albums", mutableListOf(
                    CategoryItem(16, R.drawable.icon),
                    CategoryItem(17, R.drawable.icon),
                    CategoryItem(18, R.drawable.icon),
                    CategoryItem(19, R.drawable.icon),
                    CategoryItem(20, R.drawable.icon)
                )
            ),
            Category(
                "Your Playlists", mutableListOf(
                    CategoryItem(21, R.drawable.icon),
                    CategoryItem(22, R.drawable.icon),
                    CategoryItem(23, R.drawable.icon),
                    CategoryItem(24, R.drawable.icon),
                    CategoryItem(25, R.drawable.icon)
                )
            ),
            Category(
                "Recommendations", mutableListOf(
                    CategoryItem(26, R.drawable.icon),
                    CategoryItem(27, R.drawable.icon),
                    CategoryItem(28, R.drawable.icon),
                    CategoryItem(29, R.drawable.icon),
                    CategoryItem(30, R.drawable.icon)
                )
            )
        )

        val listenerCategoryData = mutableListOf<Category>(
            Category("Your Playlists", mutableListOf()),
            Category("Favourite Songs", mutableListOf()),
            Category("Favourite Albums", mutableListOf()),
            Category("Favourite Artists", mutableListOf()),
            Category("Recommendations", mutableListOf())
        )
    }
}