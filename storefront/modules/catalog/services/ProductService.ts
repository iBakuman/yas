import { ProductSlug } from '../../cart/models/ProductSlug';
import { ProductDetail } from '../models/ProductDetail';
import { ProductAll, ProductFeature } from '../models/ProductFeature';
import { ProductOptionValueGet } from '../models/ProductOptionValueGet';
import { ProductVariation } from '../models/ProductVariation';
import { ProductsGet } from '../models/ProductsGet';
import { SimilarProduct } from '../models/SimilarProduct';

export async function getFeaturedProducts(pageNo: number): Promise<ProductFeature> {
  const response = await fetch(`api/product/storefront/products/featured?pageNo=${pageNo}`);
  return response.json();
}

export async function getProductDetail(slug: string): Promise<ProductDetail> {
  const response = await fetch(process.env.API_BASE_PATH + '/product/storefront/product/' + slug);
  return response.json();
}

export async function getProductOptionValues(productId: number): Promise<ProductOptionValueGet[]> {
  const res = await fetch(
    `${process.env.API_BASE_PATH}/product/storefront/product-option-combinations/${productId}/values`
  );
  if (res.status >= 200 && res.status < 300) return res.json();
  return Promise.reject(res);
}

export async function getProductByMultiParams(queryString: string): Promise<ProductAll> {
  const res = await fetch(`/api/product/storefront/products?${queryString}`);
  return res.json();
}

export async function getProductVariationsByParentId(
  parentId: number
): Promise<ProductVariation[]> {
  const res = await fetch(
    `${process.env.API_BASE_PATH}/product/storefront/product-variations/${parentId}`
  );
  if (res.status >= 200 && res.status < 300) return res.json();
  return Promise.reject(res);
}

export async function getProductSlug(productId: number): Promise<ProductSlug> {
  const res = await fetch(`/api/product/storefront/productions/${productId}/slug`);
  if (res.status >= 200 && res.status < 300) return res.json();
  return Promise.reject(res);
}

export async function getRelatedProductsByProductId(productId: number): Promise<ProductsGet> {
  const res = await fetch(`/api/product/storefront/products/related-products/${productId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  if (res.status >= 200 && res.status < 300) return res.json();
  return res.json().then((errorData) => {
    const error = new Error('HTTP error: ' + res.statusText);
    error.status = res.status;
    error.data = errorData; // Attach the response data for debugging
    return Promise.reject(error);
  });
}

export async function getSimilarProductsByProductId(productId: number): Promise<SimilarProduct[]> {
  const res = await fetch(`/api/recommendation/embedding/product/${productId}/similarity`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  if (res.status >= 200 && res.status < 300) return res.json();
  return Promise.reject(res);
}
