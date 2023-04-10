package com.example.shopping_list.data.room

import androidx.room.*
import com.example.shopping_list.data.room.tables.*

/* Data access object to query the database. */
@Dao
interface DataDao {


    @Insert
    fun addBasket(basket: BasketDB): Long

    @Update
    fun update(basket: BasketDB)

    @Query("DELETE FROM Basket WHERE idBasket = :id")
    fun deleteByIdBasket(id:Int)

    @Query("SELECT idBasket FROM Basket WHERE basketName = :basketName")
    fun checkBasketFromName(basketName: String): Long

    @Query("SELECT * FROM Basket")
    fun getListBasket(): List<BasketDB>

    @Transaction
    @Query("SELECT * FROM Basket WHERE idBasket = :basket_id")
    fun getBasketWithProducts(basket_id: Int): List<BasketWithProduct>

    @Transaction
    @Query("SELECT * FROM mygroup")
    fun getGroupsWithProducts(): List<GroupWithProducts>

    @Transaction
    @Query("SELECT * FROM mygroup WHERE idGroup = :id")
    fun getGroupWithProducts(id:Int): GroupWithProducts

//    @Transaction
    @Query("SELECT * FROM myunit")
    fun getUnits(): List<UnitDB>

    @Query("SELECT * FROM mygroup")
    fun getGroups(): List<GroupDB>

    @Transaction
    @Query("SELECT * FROM myunit WHERE idUnit = :id")
    fun getUnitWithProducts(id:Int): List<UnitWithProducts>

    @Insert
    fun addGroup(group: GroupDB): Long

//    @Query("SELECT * FROM baskettoproduct WHERE basket_id = :idBasket")
//    fun getBasketProducts(idBasket: Int): List<ProductDB>
    //Insert new record to the table films
//    @Insert(entity = BasketDB::class)
//    fun insert(vararg data: BasketDB)
//    //Updating a record in the table films
//    @Update
//    fun update(film: BasketDB)
//    //Clearing the table films
//    @Query("DELETE FROM films")
//    fun nukeTable()
//    //Select all entries and sort by id
//    @Query("SELECT * FROM films ORDER BY idFilm DESC")
//    fun getAll(): BasketDB
//    //Select filmDB bi id
//    @Query("SELECT * FROM films WHERE idFilm = :id")
//    fun getFilm(id: Int): BasketDB?
//    //Select all entries from films
//    @Query("SELECT * FROM films")
//    fun getFilms(): List<BasketDB>?
//    //Get the VIEWED parameter for recording with id = id
//    @Query("SELECT viewed FROM films WHERE idFilm = :id")
//    fun getViewed(id: Int): Boolean
//    //Select list value filmId from the table films where viewed = :viewed
//    @Query("SELECT filmId FROM films WHERE viewed = :viewed")
//    fun getViewedFilms(viewed: Boolean): List<Int>
//    //Setting the viewed parameter in all records
//    @Query("UPDATE films SET viewed = :value ")
//    fun setAllViewed(value: Boolean)
//    //Creating a stream to a movie in viewed state
//    @Query("SELECT viewed FROM films WHERE idFilm = :id")
//    fun setViewedFlow(id: Int): Flow<Boolean>
//    //Delete record in the table films where id=id
//    @Query("DELETE FROM films WHERE idFilm = :id")
//    fun deleteByIdFilmDB(id: Int)
//    //Selection of records from the FILES table where shs belong to the list
//    @Query("SELECT * FROM films WHERE idFilm = :listId")
//    fun getFilmInList(listId: List<Int>): List<BasketDB>
//
////Table COLLECTIONS
//    //Insert new record to the table collections
//    @Insert(entity = ProductDB::class)
//    fun insert(vararg data: ProductDB)
//    //Delete record in the table collections where id=id
//    @Query("DELETE FROM collections WHERE idCollection = :id")
//    fun deleteByIdCollection(id:Int)
//    //Select all records from the table collections
//    @Query("SELECT * FROM collections ")
//    fun getCollections(): List<ProductDB>
//    //Selecting a record from a table collections where name =
//    @Query("SELECT * FROM collections WHERE name = :collectionName ")
//    fun getCollectionRecord(collectionName: String): ProductDB?
//
////Table COLLECTIONS WITH FILM
//    //Insert new record to the table CrossFC
//    @Insert(entity = BasketToProduct::class, onConflict = OnConflictStrategy.REPLACE)
//    fun insert(vararg data: BasketToProduct)
//    //Updating a record in the table CrossFC
//    @Update
//    fun update(data: BasketToProduct)
//    // FAVORITE
//    //Creating a stream to include a movie in the favorites collection
//    @Query("SELECT value FROM crossFC WHERE collection_id = '1' AND film_id = :id")
//    fun setFavoriteFlow(id: Int): Flow<Boolean>
//    //Request for the film to belong to the favorites collection
//    @Query("SELECT value FROM crossFC WHERE collection_id = '1' AND film_id = :id")
//    fun getFavorite(id: Int): Boolean
//    //BOOKMARK
//    //Creating a stream to include a movie in the bookmark collection
//    @Query("SELECT value FROM crossFC WHERE collection_id = '2' AND film_id = :id")
//    fun setBookmarkFlow(id: Int): Flow<Boolean>
//    //Request for the film to belong to the bookmark collection
//    @Query("SELECT value FROM crossFC WHERE collection_id = '2' AND film_id = :id")
//    fun getBookmark(id: Int): Boolean
//    //Selecting a count of movies added to the collection
//    @Query("SELECT * FROM crossFC WHERE collection_id = :id")
//    fun getCountFilmCollection(id: Int): List<BasketToProduct>
//    //Request an entry from the table crossFC for the movie belonging to the collection
//    @Query("SELECT id FROM crossFC WHERE collection_id = :collectionId AND film_id = :filmId")
//    fun getFilmInCollection( filmId: Int,collectionId: Int): Int?
//    //Request for the film to belong to the collection
//    @Query("SELECT 1 FROM crossFC WHERE film_id = :filmId")
//    fun existFilmInCollections( filmId: Int): Boolean
//    //Deleting record in table crossFC by id
//    @Query("DELETE FROM crossFC WHERE id = :id")
//    fun deleteByIdCrossFC(id:Int)
//    //Clear all link collection to film for collection
//    @Query("DELETE FROM crossFC WHERE collection_id = :id")
//    fun clearByCollectionIdCrossFC(id:Int)
//    //Selecting a list of movies-id added to the collection
//    @Query("SELECT film_id FROM crossFC WHERE collection_id = :collectionId")
//    fun getListFilmsInCollection(collectionId: Int): List<Int>
}
