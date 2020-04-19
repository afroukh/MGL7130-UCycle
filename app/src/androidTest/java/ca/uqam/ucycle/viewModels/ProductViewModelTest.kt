package ca.uqam.ucycle.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ca.uqam.ucycle.models.Product
import ca.uqam.ucycle.ui.product.ProductsViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

class ProductViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductsViewModel

    private val observer: Observer<Product> = mock()

    @Before
    fun before() {
        viewModel = ProductsViewModel()
        viewModel.product.observeForever(observer)
    }

    @Test
    fun getProduct_ShouldReturnProduct() {
        val expectedProduct = Product("87UK8","Vintage TV","Still  in good Shape","Montreal",
            urlImage = null)

        viewModel.getProduct(expectedProduct.id!!,"CAT1")

        val captor = ArgumentCaptor.forClass(Product::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(expectedProduct.id, value.id)
        }
    }


}