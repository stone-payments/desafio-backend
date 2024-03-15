<?php

namespace App\Http\Controllers;

use App\Http\Requests\product\StoreProductRequest;
use App\Http\Requests\product\UpdateProductRequest;
use Illuminate\Http\Request;
use App\Models\Product;
use Carbon\Carbon;
use Illuminate\Auth\Events\Validated;
use Illuminate\Support\Facades\Validator;
use Spatie\FlareClient\Http\Exceptions\NotFound;

class ProductController extends Controller
{
    function index()
    {
        return Product::all();
    }

    function show($id)
    {
        $product = Product::find($id);

        if (!$product)
            return response()->json(['message' => 'Product not found'], 404);
        return response()->json(['product' => $product], 200);
    }

    function store(StoreProductRequest $request)
    {
        $validated = $request->validated();

        // formatting to save in the database
        $validated['date'] = Carbon::createFromFormat('d/m/Y', $validated['date'])->format('Y-m-d');

        $product = Product::create($validated);

        return response()->json(['message' => 'Product created', 'product' => $product], 201);
    }

    function update(UpdateProductRequest $request, $id)
    {
        $validated = $request->validated();

        $product = Product::find($id);
        if (!$product)
            return response()->json(['message' => 'Product not found'], 404);

        // formatting to save in the database
        $validated['date'] = Carbon::createFromFormat('d/m/Y', $validated['date'])->format('Y-m-d');

        $product->update($validated);

        return response()->json(['message' => 'Product updated', 'product' => $product], 200);
    }

    function destroy($id)
    {
        $product = Product::find($id);
        if (!$product)
            return response()->json(['message' => 'Product not found'], 404);

        $product->delete();
        return response()->json(['message' => 'Product deleted'], 204);
    }
}
