package com.tecsup.candidatoinfo.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class PreferencesManager(private val context: Context) {

    companion object {
        private val SELECTED_REGION_ID = intPreferencesKey("selected_region_id")
        private val SELECTED_REGION_NAME = stringPreferencesKey("selected_region_name")
    }

    val hasSelectedRegion: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_REGION_ID] != null
    }

    val selectedRegionId: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_REGION_ID]
    }

    val selectedRegionName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_REGION_NAME]
    }

    suspend fun saveSelectedRegion(regionId: Int, regionName: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_REGION_ID] = regionId
            preferences[SELECTED_REGION_NAME] = regionName
        }
    }

    suspend fun clearSelectedRegion() {
        context.dataStore.edit { preferences ->
            preferences.remove(SELECTED_REGION_ID)
            preferences.remove(SELECTED_REGION_NAME)
        }
    }
}