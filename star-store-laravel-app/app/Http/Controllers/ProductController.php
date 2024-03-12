<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Product;

class ProductController extends Controller
{
    function index()
    {
        return Product::all();
    }

    function show(Product $product)
    {
        return Product::find($product);
    }

    function store(Request $request)
    {
        $validated = $request->validate([
            'title' => 'required',
            'price' => 'required|min:1',
            'zipcode' => 'required',
            'seller' => 'required',
            'thumbnailHd' => 'required',
            'date' => 'required|date_format:d/m/Y'
        ]);
        if ($validated){
            $product = Product::create($validated);
            return response()->json(['message' => 'Product created', 'product' => $product], 201);
        }
        return response()->json(['message' => 'erro ao criar testamento'], 400);
    }

    function update(Request $request, Product $product)
    {
        $validated = $request->validate([
            'title' => 'required',
            'price' => 'required',
            'zipcode' => 'required',
            'seller' => 'required',
            'thumbnailHd' => 'required',
            'date' => 'required'
        ]);

        $product->update($validated);

        return response()->json(['message' => 'Product updated', 'product' => $product], 200);
    }

    function destroy(Product $product)
    {
        $product->delete();
        return response()->json(['message' => 'Product deleted'], 200);
    }
}
