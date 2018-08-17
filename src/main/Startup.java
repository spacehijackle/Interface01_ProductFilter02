package main;

import java.util.ArrayList;
import java.util.List;

import data.Product;
import filter.ProductFilter;

/**
 * 商品を絞り込むプログラム例
 *
 * @author t.yoshida
 */
public class Startup
{
	public static void main(String[] args)
	{
		// オリジナル商品リスト
		List<Product> products = createProducts();
		showProducts("オリジナル商品リスト", products);

		// 絞り込み後商品リスト定義
		List<Product> filteredProducts;

		// 100円ポッキリの商品に絞り込み
		filteredProducts = applyFilter(products, new ProductFilter()
		{
			@Override
			public boolean isMatches(Product target)
			{
				return (target.getPrice() == 100);
			}
		});
		showProducts("100円ポッキリ！", filteredProducts);

		// 1,000円以上 2,000円以下の商品に絞り込み
		ProductFilter filter1 = new ProductFilter()
		{
			@Override
			public boolean isMatches(Product target)
			{
				int price = target.getPrice();
				return (price >= 1_000 && price <= 2_000);
			}
		};
		filteredProducts = applyFilter(products, filter1);
		showProducts("千円～二千円で買える！", filteredProducts);

		// 1.5Kg以下の商品に絞り込み
		filteredProducts = applyFilter
		(
			products, (Product target) -> { return (target.getWeight() <= 1_500); }
		);
		showProducts("片手で持てる！", filteredProducts);

		// ワンコインで買える商品に絞り込み
		ProductFilter filter2 = (target) -> { return (target.getPrice() <= 500); };
		//ProductFilter filter2 = target -> target.getPrice() <= 500;
		filteredProducts = applyFilter(products, filter2);
		showProducts("ワンコインで買える！", filteredProducts);
	}

	/**
	 * 商品リストを作成して返す。
	 *
	 * @return 商品リスト
	 */
	private static List<Product> createProducts()
	{
		List<Product> products = new ArrayList<>();
		products.add(new Product("テントウムシ 1,500匹", 780, 453));
		products.add(new Product("５億ジンバブエドル", 3_000, 1));
		products.add(new Product("100円硬貨", 100, 5));
		products.add(new Product("ユニコーン缶詰肉", 1_240, 100));
		products.add(new Product("豚の胎児【研究用】", 1_950, 996));
		products.add(new Product("エマニエル・チェア", 1_880, 2_880));
		products.add(new Product("18金製 黄金風呂", 150_000_000, 80_000));

		return products;
	}

	/**
	 * 商品リストを画面表示する。
	 *
	 * @param title 表示タイトル
	 * @param products 商品リスト
	 */
	private static void showProducts(String title, List<Product> products)
	{
		System.out.println("─────────────────────────────");
		System.out.println("# " + title);
		System.out.println("─────────────────────────────");
		for(Product product : products)
		{
			System.out.println(product);
		}
		System.out.println("─────────────────────────────");
	}

	/**
	 * 指定された絞り込み条件に合致する商品のみに絞る。
	 *
	 * @param products 絞り込み前の商品リスト
	 * @param filter 商品絞り込みフィルター
	 * @return 絞り込み後の商品リスト
	 */
	private static List<Product> applyFilter(List<Product> products, ProductFilter filter)
	{
		List<Product> list = new ArrayList<>();
		for(Product product : products)
		{
			if(filter.isMatches(product))
			{
				list.add(product);
			}
		}

		return list;
	}
}
