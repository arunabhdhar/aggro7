package com.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;

/**
 * Created by ericbasendra on 20/10/15.
 */
public class AppLibrary extends CardArrayRecyclerViewAdapter {

	/**
	 * Internal objects
	 */
	protected List<Card> mCards;
	/**
	 * Constructor
	 *
	 * @param context The current context.
	 * @param cards   The cards to represent in the ListView.
	 */


	public AppLibrary(Context context, List<Card> cards) {
		super(context, cards);
		if (cards != null) {
			mCards = cards;
		} else {
			mCards = new ArrayList<Card>();
		}
	}

	// -------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------

	@Override
	public int getItemCount() {
		return mCards.size();
	}


	@Override
	public Card getItem(int position) {
		return mCards.get(position);
	}

	/**
	 * Sets the card's list
	 * @param cards list
	 */
	public void setCards(List<Card> cards) {
		mCards = cards;
	}


	/**
	 * Appends the specified element to the end of the {@code List}.
	 *
	 * @param card the object to add.
	 *
	 * @return always true.
	 */
	@Override
	public boolean add(@NonNull final Card card) {
		boolean result = mCards.add(card);
		notifyDataSetChanged();
		return result;
	}

	/**
	 * Appends the specified element into the index specified {@code List}.
	 * @param index
	 * @param card
	 */
	@Override
	public void add(final int index, @NonNull final Card card) {
		mCards.add(index, card);
		notifyItemInserted(index);
	}

	/**
	 * Adds the objects in the specified collection to the end of this List. The objects are added in the order in which they are returned from the collection's iterator.
	 *
	 * @param collection the collection of objects.
	 *
	 * @return {@code true} if this {@code List} is modified, {@code false} otherwise.
	 */
	public boolean addAll(@NonNull final Collection<? extends Card> collection) {
		boolean result = mCards.addAll(collection);
		notifyDataSetChanged();
		return result;
	}

	/**
	 * Check if the list contains the element
	 * @param card
	 * @return
	 */
	@Override
	public boolean contains(final Card card) {
		return mCards.contains(card);
	}

	/**
	 * Clears the list
	 */
	@Override
	public void clear() {
		mCards.clear();
		notifyDataSetChanged();
	}

	/**
	 * Removes the specified element
	 * @param card
	 * @return
	 */
	@Override
	public boolean remove(@NonNull final Card card) {
		boolean result = mCards.remove(card);
		notifyDataSetChanged();
		return result;
	}

	/**
	 * Removes the element at position
	 * @param position
	 * @return
	 */
	@NonNull
	@Override
	public Card remove(final int position) {
		Card result = mCards.remove(position);
		notifyItemRemoved(position);
		return result;
	}


}
