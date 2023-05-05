package com.example.shopping_list.data.room;

import android.database.Cursor;
import androidx.collection.LongSparseArray;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.shopping_list.data.room.tables.ArticleEntity;
import com.example.shopping_list.data.room.tables.BasketEntity;
import com.example.shopping_list.data.room.tables.GroupEntity;
import com.example.shopping_list.data.room.tables.ProductEntity;
import com.example.shopping_list.data.room.tables.UnitEntity;
import com.example.shopping_list.data.room.tables.relation.ArticleObj;
import com.example.shopping_list.data.room.tables.relation.ProductObj;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DataDao_Impl implements DataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BasketEntity> __insertionAdapterOfBasketEntity;

  private final EntityInsertionAdapter<ProductEntity> __insertionAdapterOfProductEntity;

  private final EntityInsertionAdapter<ArticleEntity> __insertionAdapterOfArticleEntity;

  private final EntityInsertionAdapter<GroupEntity> __insertionAdapterOfGroupEntity;

  private final EntityInsertionAdapter<UnitEntity> __insertionAdapterOfUnitEntity;

  private final EntityDeletionOrUpdateAdapter<BasketEntity> __updateAdapterOfBasketEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByIdBasket;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSelectedProduct;

  private final SharedSQLiteStatement __preparedStmtOfSetValueProduct;

  private final SharedSQLiteStatement __preparedStmtOfSetUnitInArticle;

  private final SharedSQLiteStatement __preparedStmtOfPutProductInBasket;

  public DataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBasketEntity = new EntityInsertionAdapter<BasketEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `tb_basket` (`idBasket`,`nameBasket`,`fillBasket`,`selected`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BasketEntity value) {
        stmt.bindLong(1, value.getIdBasket());
        if (value.getNameBasket() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameBasket());
        }
        final int _tmp = value.getFillBasket() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        final int _tmp_1 = value.getSelected() ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
      }
    };
    this.__insertionAdapterOfProductEntity = new EntityInsertionAdapter<ProductEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `tb_product` (`idProduct`,`value`,`basketId`,`putInBasket`,`articleId`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ProductEntity value) {
        stmt.bindLong(1, value.getIdProduct());
        stmt.bindDouble(2, value.getValue());
        if (value.getBasketId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.getBasketId());
        }
        final int _tmp = value.getPutInBasket() ? 1 : 0;
        stmt.bindLong(4, _tmp);
        if (value.getArticleId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getArticleId());
        }
      }
    };
    this.__insertionAdapterOfArticleEntity = new EntityInsertionAdapter<ArticleEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `tb_article` (`idArticle`,`nameArticle`,`groupId`,`unitId`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ArticleEntity value) {
        stmt.bindLong(1, value.getIdArticle());
        if (value.getNameArticle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameArticle());
        }
        if (value.getGroupId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.getGroupId());
        }
        if (value.getUnitId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getUnitId());
        }
      }
    };
    this.__insertionAdapterOfGroupEntity = new EntityInsertionAdapter<GroupEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `tb_group` (`idGroup`,`nameGroup`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GroupEntity value) {
        stmt.bindLong(1, value.getIdGroup());
        if (value.getNameGroup() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameGroup());
        }
      }
    };
    this.__insertionAdapterOfUnitEntity = new EntityInsertionAdapter<UnitEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `tb_unit` (`idUnit`,`nameUnit`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UnitEntity value) {
        stmt.bindLong(1, value.getIdUnit());
        if (value.getNameUnit() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameUnit());
        }
      }
    };
    this.__updateAdapterOfBasketEntity = new EntityDeletionOrUpdateAdapter<BasketEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `tb_basket` SET `idBasket` = ?,`nameBasket` = ?,`fillBasket` = ?,`selected` = ? WHERE `idBasket` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BasketEntity value) {
        stmt.bindLong(1, value.getIdBasket());
        if (value.getNameBasket() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameBasket());
        }
        final int _tmp = value.getFillBasket() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        final int _tmp_1 = value.getSelected() ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
        stmt.bindLong(5, value.getIdBasket());
      }
    };
    this.__preparedStmtOfDeleteByIdBasket = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM tb_basket WHERE idBasket = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSelectedProduct = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM tb_product WHERE idProduct=? AND basketId =?";
        return _query;
      }
    };
    this.__preparedStmtOfSetValueProduct = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE tb_product SET value = ? WHERE  idProduct=? AND basketId =? ";
        return _query;
      }
    };
    this.__preparedStmtOfSetUnitInArticle = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE tb_article SET unitId = ? WHERE idArticle =?";
        return _query;
      }
    };
    this.__preparedStmtOfPutProductInBasket = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE tb_product SET putInBasket = NOT putInBasket WHERE idProduct=? AND basketId =? ";
        return _query;
      }
    };
  }

  @Override
  public long newBasket(final BasketEntity basket) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfBasketEntity.insertAndReturnId(basket);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long addProduct(final ProductEntity product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfProductEntity.insertAndReturnId(product);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long addArticle(final ArticleEntity article) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfArticleEntity.insertAndReturnId(article);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long addGroup(final GroupEntity group) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfGroupEntity.insertAndReturnId(group);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long addUnit(final UnitEntity unit) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfUnitEntity.insertAndReturnId(unit);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final BasketEntity basket) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfBasketEntity.handle(basket);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteByIdBasket(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByIdBasket.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByIdBasket.release(_stmt);
    }
  }

  @Override
  public void deleteSelectedProduct(final long productId, final long basketId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSelectedProduct.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, productId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, basketId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteSelectedProduct.release(_stmt);
    }
  }

  @Override
  public void setValueProduct(final long productId, final long basketId, final double value) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetValueProduct.acquire();
    int _argIndex = 1;
    _stmt.bindDouble(_argIndex, value);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, productId);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, basketId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfSetValueProduct.release(_stmt);
    }
  }

  @Override
  public void setUnitInArticle(final long articleId, final long unitId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetUnitInArticle.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, unitId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, articleId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfSetUnitInArticle.release(_stmt);
    }
  }

  @Override
  public void putProductInBasket(final long productId, final long basketId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfPutProductInBasket.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, productId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, basketId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfPutProductInBasket.release(_stmt);
    }
  }

  @Override
  public Long checkBasketFromName(final String basketName) {
    final String _sql = "SELECT idBasket FROM tb_basket WHERE nameBasket = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (basketName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, basketName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Long _result;
      if(_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getLong(0);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<BasketEntity> getListBasket() {
    final String _sql = "SELECT * FROM tb_basket";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdBasket = CursorUtil.getColumnIndexOrThrow(_cursor, "idBasket");
      final int _cursorIndexOfNameBasket = CursorUtil.getColumnIndexOrThrow(_cursor, "nameBasket");
      final int _cursorIndexOfFillBasket = CursorUtil.getColumnIndexOrThrow(_cursor, "fillBasket");
      final int _cursorIndexOfSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "selected");
      final List<BasketEntity> _result = new ArrayList<BasketEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final BasketEntity _item;
        final long _tmpIdBasket;
        _tmpIdBasket = _cursor.getLong(_cursorIndexOfIdBasket);
        final String _tmpNameBasket;
        if (_cursor.isNull(_cursorIndexOfNameBasket)) {
          _tmpNameBasket = null;
        } else {
          _tmpNameBasket = _cursor.getString(_cursorIndexOfNameBasket);
        }
        final boolean _tmpFillBasket;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFillBasket);
        _tmpFillBasket = _tmp != 0;
        final boolean _tmpSelected;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfSelected);
        _tmpSelected = _tmp_1 != 0;
        _item = new BasketEntity(_tmpIdBasket,_tmpNameBasket,_tmpFillBasket,_tmpSelected);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Long checkProductFromName(final String name) {
    final String _sql = "SELECT idProduct FROM tb_product JOIN tb_article ON tb_article.nameArticle = ? WHERE tb_product.articleId = tb_article.idArticle";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Long _result;
      if(_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getLong(0);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Long checkProductInBasket(final long basketId, final long productId) {
    final String _sql = "SELECT idProduct FROM tb_product WHERE idProduct = ? AND basketId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, basketId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Long _result;
      if(_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getLong(0);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ProductObj> getListProduct(final long basketId) {
    final String _sql = "SELECT * FROM tb_product WHERE basketId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, basketId);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfIdProduct = CursorUtil.getColumnIndexOrThrow(_cursor, "idProduct");
        final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
        final int _cursorIndexOfBasketId = CursorUtil.getColumnIndexOrThrow(_cursor, "basketId");
        final int _cursorIndexOfPutInBasket = CursorUtil.getColumnIndexOrThrow(_cursor, "putInBasket");
        final int _cursorIndexOfArticleId = CursorUtil.getColumnIndexOrThrow(_cursor, "articleId");
        final LongSparseArray<ArticleObj> _collectionArticle = new LongSparseArray<ArticleObj>();
        while (_cursor.moveToNext()) {
          if (!_cursor.isNull(_cursorIndexOfArticleId)) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfArticleId);
            _collectionArticle.put(_tmpKey, null);
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshiptbArticleAscomExampleShoppingListDataRoomTablesRelationArticleObj(_collectionArticle);
        final List<ProductObj> _result = new ArrayList<ProductObj>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final ProductObj _item;
          final ProductEntity _tmpProduct;
          _tmpProduct = new ProductEntity();
          final long _tmpIdProduct;
          _tmpIdProduct = _cursor.getLong(_cursorIndexOfIdProduct);
          _tmpProduct.setIdProduct(_tmpIdProduct);
          final double _tmpValue;
          _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
          _tmpProduct.setValue(_tmpValue);
          final Long _tmpBasketId;
          if (_cursor.isNull(_cursorIndexOfBasketId)) {
            _tmpBasketId = null;
          } else {
            _tmpBasketId = _cursor.getLong(_cursorIndexOfBasketId);
          }
          _tmpProduct.setBasketId(_tmpBasketId);
          final boolean _tmpPutInBasket;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfPutInBasket);
          _tmpPutInBasket = _tmp != 0;
          _tmpProduct.setPutInBasket(_tmpPutInBasket);
          final Long _tmpArticleId;
          if (_cursor.isNull(_cursorIndexOfArticleId)) {
            _tmpArticleId = null;
          } else {
            _tmpArticleId = _cursor.getLong(_cursorIndexOfArticleId);
          }
          _tmpProduct.setArticleId(_tmpArticleId);
          ArticleObj _tmpArticle = null;
          if (!_cursor.isNull(_cursorIndexOfArticleId)) {
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfArticleId);
            _tmpArticle = _collectionArticle.get(_tmpKey_1);
          }
          _item = new ProductObj(_tmpProduct,_tmpArticle);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<ProductObj> getListProductAll() {
    final String _sql = "SELECT * FROM tb_product";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfIdProduct = CursorUtil.getColumnIndexOrThrow(_cursor, "idProduct");
        final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
        final int _cursorIndexOfBasketId = CursorUtil.getColumnIndexOrThrow(_cursor, "basketId");
        final int _cursorIndexOfPutInBasket = CursorUtil.getColumnIndexOrThrow(_cursor, "putInBasket");
        final int _cursorIndexOfArticleId = CursorUtil.getColumnIndexOrThrow(_cursor, "articleId");
        final LongSparseArray<ArticleObj> _collectionArticle = new LongSparseArray<ArticleObj>();
        while (_cursor.moveToNext()) {
          if (!_cursor.isNull(_cursorIndexOfArticleId)) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfArticleId);
            _collectionArticle.put(_tmpKey, null);
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshiptbArticleAscomExampleShoppingListDataRoomTablesRelationArticleObj(_collectionArticle);
        final List<ProductObj> _result = new ArrayList<ProductObj>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final ProductObj _item;
          final ProductEntity _tmpProduct;
          _tmpProduct = new ProductEntity();
          final long _tmpIdProduct;
          _tmpIdProduct = _cursor.getLong(_cursorIndexOfIdProduct);
          _tmpProduct.setIdProduct(_tmpIdProduct);
          final double _tmpValue;
          _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
          _tmpProduct.setValue(_tmpValue);
          final Long _tmpBasketId;
          if (_cursor.isNull(_cursorIndexOfBasketId)) {
            _tmpBasketId = null;
          } else {
            _tmpBasketId = _cursor.getLong(_cursorIndexOfBasketId);
          }
          _tmpProduct.setBasketId(_tmpBasketId);
          final boolean _tmpPutInBasket;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfPutInBasket);
          _tmpPutInBasket = _tmp != 0;
          _tmpProduct.setPutInBasket(_tmpPutInBasket);
          final Long _tmpArticleId;
          if (_cursor.isNull(_cursorIndexOfArticleId)) {
            _tmpArticleId = null;
          } else {
            _tmpArticleId = _cursor.getLong(_cursorIndexOfArticleId);
          }
          _tmpProduct.setArticleId(_tmpArticleId);
          ArticleObj _tmpArticle = null;
          if (!_cursor.isNull(_cursorIndexOfArticleId)) {
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfArticleId);
            _tmpArticle = _collectionArticle.get(_tmpKey_1);
          }
          _item = new ProductObj(_tmpProduct,_tmpArticle);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<ArticleObj> getListArticle() {
    final String _sql = "SELECT * FROM tb_article ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfIdArticle = CursorUtil.getColumnIndexOrThrow(_cursor, "idArticle");
        final int _cursorIndexOfNameArticle = CursorUtil.getColumnIndexOrThrow(_cursor, "nameArticle");
        final int _cursorIndexOfGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "groupId");
        final int _cursorIndexOfUnitId = CursorUtil.getColumnIndexOrThrow(_cursor, "unitId");
        final LongSparseArray<UnitEntity> _collectionUnitA = new LongSparseArray<UnitEntity>();
        final LongSparseArray<GroupEntity> _collectionGroup = new LongSparseArray<GroupEntity>();
        while (_cursor.moveToNext()) {
          if (!_cursor.isNull(_cursorIndexOfUnitId)) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfUnitId);
            _collectionUnitA.put(_tmpKey, null);
          }
          if (!_cursor.isNull(_cursorIndexOfGroupId)) {
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfGroupId);
            _collectionGroup.put(_tmpKey_1, null);
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshiptbUnitAscomExampleShoppingListDataRoomTablesUnitEntity(_collectionUnitA);
        __fetchRelationshiptbGroupAscomExampleShoppingListDataRoomTablesGroupEntity(_collectionGroup);
        final List<ArticleObj> _result = new ArrayList<ArticleObj>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final ArticleObj _item;
          final ArticleEntity _tmpArticle;
          _tmpArticle = new ArticleEntity();
          final long _tmpIdArticle;
          _tmpIdArticle = _cursor.getLong(_cursorIndexOfIdArticle);
          _tmpArticle.setIdArticle(_tmpIdArticle);
          final String _tmpNameArticle;
          if (_cursor.isNull(_cursorIndexOfNameArticle)) {
            _tmpNameArticle = null;
          } else {
            _tmpNameArticle = _cursor.getString(_cursorIndexOfNameArticle);
          }
          _tmpArticle.setNameArticle(_tmpNameArticle);
          final Long _tmpGroupId;
          if (_cursor.isNull(_cursorIndexOfGroupId)) {
            _tmpGroupId = null;
          } else {
            _tmpGroupId = _cursor.getLong(_cursorIndexOfGroupId);
          }
          _tmpArticle.setGroupId(_tmpGroupId);
          final Long _tmpUnitId;
          if (_cursor.isNull(_cursorIndexOfUnitId)) {
            _tmpUnitId = null;
          } else {
            _tmpUnitId = _cursor.getLong(_cursorIndexOfUnitId);
          }
          _tmpArticle.setUnitId(_tmpUnitId);
          UnitEntity _tmpUnitA = null;
          if (!_cursor.isNull(_cursorIndexOfUnitId)) {
            final long _tmpKey_2 = _cursor.getLong(_cursorIndexOfUnitId);
            _tmpUnitA = _collectionUnitA.get(_tmpKey_2);
          }
          GroupEntity _tmpGroup = null;
          if (!_cursor.isNull(_cursorIndexOfGroupId)) {
            final long _tmpKey_3 = _cursor.getLong(_cursorIndexOfGroupId);
            _tmpGroup = _collectionGroup.get(_tmpKey_3);
          }
          _item = new ArticleObj(_tmpArticle,_tmpUnitA,_tmpGroup);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long getIdUnitFromArticle(final long articleId) {
    final String _sql = "SELECT unitId FROM tb_article WHERE idArticle =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, articleId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0L;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<GroupEntity> getGroups() {
    final String _sql = "SELECT * FROM tb_group";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "idGroup");
      final int _cursorIndexOfNameGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "nameGroup");
      final List<GroupEntity> _result = new ArrayList<GroupEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final GroupEntity _item;
        final long _tmpIdGroup;
        _tmpIdGroup = _cursor.getLong(_cursorIndexOfIdGroup);
        final String _tmpNameGroup;
        if (_cursor.isNull(_cursorIndexOfNameGroup)) {
          _tmpNameGroup = null;
        } else {
          _tmpNameGroup = _cursor.getString(_cursorIndexOfNameGroup);
        }
        _item = new GroupEntity(_tmpIdGroup,_tmpNameGroup);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<UnitEntity> getUnits() {
    final String _sql = "SELECT * FROM tb_unit";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "idUnit");
      final int _cursorIndexOfNameUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "nameUnit");
      final List<UnitEntity> _result = new ArrayList<UnitEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UnitEntity _item;
        final long _tmpIdUnit;
        _tmpIdUnit = _cursor.getLong(_cursorIndexOfIdUnit);
        final String _tmpNameUnit;
        if (_cursor.isNull(_cursorIndexOfNameUnit)) {
          _tmpNameUnit = null;
        } else {
          _tmpNameUnit = _cursor.getString(_cursorIndexOfNameUnit);
        }
        _item = new UnitEntity(_tmpIdUnit,_tmpNameUnit);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshiptbUnitAscomExampleShoppingListDataRoomTablesUnitEntity(
      final LongSparseArray<UnitEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<UnitEntity> _tmpInnerMap = new LongSparseArray<UnitEntity>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), null);
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptbUnitAscomExampleShoppingListDataRoomTablesUnitEntity(_tmpInnerMap);
          _map.putAll(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<UnitEntity>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptbUnitAscomExampleShoppingListDataRoomTablesUnitEntity(_tmpInnerMap);
        _map.putAll(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `idUnit`,`nameUnit` FROM `tb_unit` WHERE `idUnit` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "idUnit");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfIdUnit = 0;
      final int _cursorIndexOfNameUnit = 1;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          if (_map.containsKey(_tmpKey)) {
            final UnitEntity _item_1;
            final long _tmpIdUnit;
            _tmpIdUnit = _cursor.getLong(_cursorIndexOfIdUnit);
            final String _tmpNameUnit;
            if (_cursor.isNull(_cursorIndexOfNameUnit)) {
              _tmpNameUnit = null;
            } else {
              _tmpNameUnit = _cursor.getString(_cursorIndexOfNameUnit);
            }
            _item_1 = new UnitEntity(_tmpIdUnit,_tmpNameUnit);
            _map.put(_tmpKey, _item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptbGroupAscomExampleShoppingListDataRoomTablesGroupEntity(
      final LongSparseArray<GroupEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<GroupEntity> _tmpInnerMap = new LongSparseArray<GroupEntity>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), null);
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptbGroupAscomExampleShoppingListDataRoomTablesGroupEntity(_tmpInnerMap);
          _map.putAll(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<GroupEntity>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptbGroupAscomExampleShoppingListDataRoomTablesGroupEntity(_tmpInnerMap);
        _map.putAll(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `idGroup`,`nameGroup` FROM `tb_group` WHERE `idGroup` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "idGroup");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfIdGroup = 0;
      final int _cursorIndexOfNameGroup = 1;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          if (_map.containsKey(_tmpKey)) {
            final GroupEntity _item_1;
            final long _tmpIdGroup;
            _tmpIdGroup = _cursor.getLong(_cursorIndexOfIdGroup);
            final String _tmpNameGroup;
            if (_cursor.isNull(_cursorIndexOfNameGroup)) {
              _tmpNameGroup = null;
            } else {
              _tmpNameGroup = _cursor.getString(_cursorIndexOfNameGroup);
            }
            _item_1 = new GroupEntity(_tmpIdGroup,_tmpNameGroup);
            _map.put(_tmpKey, _item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptbArticleAscomExampleShoppingListDataRoomTablesRelationArticleObj(
      final LongSparseArray<ArticleObj> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArticleObj> _tmpInnerMap = new LongSparseArray<ArticleObj>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), null);
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptbArticleAscomExampleShoppingListDataRoomTablesRelationArticleObj(_tmpInnerMap);
          _map.putAll(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArticleObj>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptbArticleAscomExampleShoppingListDataRoomTablesRelationArticleObj(_tmpInnerMap);
        _map.putAll(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `idArticle`,`nameArticle`,`groupId`,`unitId` FROM `tb_article` WHERE `idArticle` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, true, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "idArticle");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfIdArticle = 0;
      final int _cursorIndexOfNameArticle = 1;
      final int _cursorIndexOfGroupId = 2;
      final int _cursorIndexOfUnitId = 3;
      final LongSparseArray<UnitEntity> _collectionUnitA = new LongSparseArray<UnitEntity>();
      final LongSparseArray<GroupEntity> _collectionGroup = new LongSparseArray<GroupEntity>();
      while (_cursor.moveToNext()) {
        if (!_cursor.isNull(_cursorIndexOfUnitId)) {
          final long _tmpKey = _cursor.getLong(_cursorIndexOfUnitId);
          _collectionUnitA.put(_tmpKey, null);
        }
        if (!_cursor.isNull(_cursorIndexOfGroupId)) {
          final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfGroupId);
          _collectionGroup.put(_tmpKey_1, null);
        }
      }
      _cursor.moveToPosition(-1);
      __fetchRelationshiptbUnitAscomExampleShoppingListDataRoomTablesUnitEntity(_collectionUnitA);
      __fetchRelationshiptbGroupAscomExampleShoppingListDataRoomTablesGroupEntity(_collectionGroup);
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey_2 = _cursor.getLong(_itemKeyIndex);
          if (_map.containsKey(_tmpKey_2)) {
            final ArticleObj _item_1;
            final ArticleEntity _tmpArticle;
            _tmpArticle = new ArticleEntity();
            final long _tmpIdArticle;
            _tmpIdArticle = _cursor.getLong(_cursorIndexOfIdArticle);
            _tmpArticle.setIdArticle(_tmpIdArticle);
            final String _tmpNameArticle;
            if (_cursor.isNull(_cursorIndexOfNameArticle)) {
              _tmpNameArticle = null;
            } else {
              _tmpNameArticle = _cursor.getString(_cursorIndexOfNameArticle);
            }
            _tmpArticle.setNameArticle(_tmpNameArticle);
            final Long _tmpGroupId;
            if (_cursor.isNull(_cursorIndexOfGroupId)) {
              _tmpGroupId = null;
            } else {
              _tmpGroupId = _cursor.getLong(_cursorIndexOfGroupId);
            }
            _tmpArticle.setGroupId(_tmpGroupId);
            final Long _tmpUnitId;
            if (_cursor.isNull(_cursorIndexOfUnitId)) {
              _tmpUnitId = null;
            } else {
              _tmpUnitId = _cursor.getLong(_cursorIndexOfUnitId);
            }
            _tmpArticle.setUnitId(_tmpUnitId);
            UnitEntity _tmpUnitA = null;
            if (!_cursor.isNull(_cursorIndexOfUnitId)) {
              final long _tmpKey_3 = _cursor.getLong(_cursorIndexOfUnitId);
              _tmpUnitA = _collectionUnitA.get(_tmpKey_3);
            }
            GroupEntity _tmpGroup = null;
            if (!_cursor.isNull(_cursorIndexOfGroupId)) {
              final long _tmpKey_4 = _cursor.getLong(_cursorIndexOfGroupId);
              _tmpGroup = _collectionGroup.get(_tmpKey_4);
            }
            _item_1 = new ArticleObj(_tmpArticle,_tmpUnitA,_tmpGroup);
            _map.put(_tmpKey_2, _item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
