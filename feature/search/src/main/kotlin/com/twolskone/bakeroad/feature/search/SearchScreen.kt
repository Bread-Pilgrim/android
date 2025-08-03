package com.twolskone.bakeroad.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twolskone.bakeroad.core.designsystem.component.button.BakeRoadTextButton
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonSize
import com.twolskone.bakeroad.core.designsystem.component.button.TextButtonStyle
import com.twolskone.bakeroad.core.designsystem.component.topbar.BakeRoadSearchTopAppBar
import com.twolskone.bakeroad.core.designsystem.theme.BakeRoadTheme
import com.twolskone.bakeroad.core.ui.EmptyCard
import com.twolskone.bakeroad.feature.search.component.RecentSearchQueryChip
import com.twolskone.bakeroad.feature.search.mvi.SearchState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    state: SearchState,
    queryTextState: TextFieldState
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocus by interactionSource.collectIsFocusedAsState()
    val canBack by remember { derivedStateOf { isFocus || queryTextState.text.isNotEmpty() } }
    val focusManager = LocalFocusManager.current

    BackHandler {
        if (canBack) {
            focusManager.clearFocus()
            queryTextState.clearText()
        } else {

        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = BakeRoadTheme.colorScheme.White)
            .padding(bottom = padding.calculateBottomPadding()),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {
        item {
            BakeRoadSearchTopAppBar(
                state = queryTextState,
                interactionSource = interactionSource,
                showBackIcon = canBack,
                hint = stringResource(id = R.string.feature_search_hint),
                onBackClick = {
                    focusManager.clearFocus()
                    queryTextState.clearText()
                }
            )
        }
    }
}

private fun LazyListScope.recentSearchResult() {}

private fun LazyListScope.recentSearchQueries(recentQueryList: ImmutableList<Pair<String, Long>>) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.feature_search_title_recent_search_query),
                style = BakeRoadTheme.typography.bodyLargeSemibold
            )
            BakeRoadTextButton(
                style = TextButtonStyle.ASSISTIVE,
                size = TextButtonSize.SMALL,
                enabled = recentQueryList.isNotEmpty(),
                onClick = {},
                content = { Text(text = stringResource(id = R.string.feature_search_button_delete_all)) }
            )
        }
        if (recentQueryList.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = recentQueryList,
                    key = { "${it.first}_${it.second}" }
                ) { (query, timeMillis) ->
                    RecentSearchQueryChip(
                        query = query,
                        onClick = {},
                        onDeleteClick = {}
                    )
                }
            }
        } else {
            EmptyCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                description = stringResource(id = R.string.feature_search_empty_recent_search_query)
            )
        }
    }
}

private fun LazyListScope.searchResult() {}

@Preview
@Composable
private fun RecentSearchQueriesPreview() {
    BakeRoadTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BakeRoadTheme.colorScheme.White)
        ) {
            recentSearchQueries(
                recentQueryList = persistentListOf("서라당" to 20, "밸런스 베이커리" to 19)
            )
        }
    }
}